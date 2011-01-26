//
//  SocketController.m
//  SMS Tether
//
//  http://code.google.com/p/sms-tether
//

#import "SocketController.h"
#import "AsyncSocket.h"

@implementation SocketController

- (id)init
{
	if(self = [super init])
	{
		listenSocket = [[AsyncSocket alloc] initWithDelegate:self];
		connectedSockets = [[NSMutableArray alloc] initWithCapacity:1];
		
		isRunning = NO;
	}
	return self;
}

- (void)applicationDidFinishLaunching:(NSNotification *)aNotification
{
	NSString *ipAddr = [[[NSHost currentHost] addresses] objectAtIndex:1];
	[mainWindow setTitle:[NSString stringWithFormat:@"SMS Tether - IP: %@", ipAddr]];

	// Advanced options - enable the socket to contine operations even during modal dialogs, and menu browsing
	[listenSocket setRunLoopModes:[NSArray arrayWithObject:NSRunLoopCommonModes]];
	
	//[self startSocket]; //Override to start the socket immediately upon launch
}

-(IBAction)startSocketServer:(id)sender {

	if(!isRunning)
	{
		[sender setLabel:@"Stop"];
	}
	else if(isRunning) {
		[sender setLabel:@"Start"];
	}
	
	[self startSocket];
}

-(void)startSocket {
	
	if(!isRunning)
	{
		int port = 58149; //Not on the list of commonly used ports on Apple's site

		NSError *error = nil;
		if(![listenSocket acceptOnPort:port error:&error])
		{
			NSLog(@"Error starting server: %@", error);
			return;
		}
		
		NSLog(@"Server started on port %hu", [listenSocket localPort]);
		isRunning = YES;

	}
	else
	{
		[listenSocket disconnect];

		int i;
		for(i = 0; i < [connectedSockets count]; i++)
		{
			[[connectedSockets objectAtIndex:i] disconnect];
		}
		
		NSLog(@"Stopped SMS receiver server");
		isRunning = NO;
	}
}

- (void)onSocket:(AsyncSocket *)sock didAcceptNewSocket:(AsyncSocket *)newSocket
{
	[connectedSockets addObject:newSocket];
}

- (void)onSocket:(AsyncSocket *)sock didConnectToHost:(NSString *)host port:(UInt16)port
{
	NSLog(@"Accepted client %@:%hu", host, port);
	
	NSString *welcomeMsg = @"ACK-L9gyYX-SERVER\r\n";
	NSData *welcomeData = [welcomeMsg dataUsingEncoding:NSUTF8StringEncoding];
	
	[sock writeData:welcomeData withTimeout:-1 tag:1];
}

- (void)onSocket:(AsyncSocket *)sock didWriteDataWithTag:(long)tag
{
	[sock readDataToData:[AsyncSocket CRLFData] withTimeout:-1 tag:0];
}

- (void)onSocket:(AsyncSocket *)sock didReadData:(NSData *)data withTag:(long)tag
{
	NSData *strData = [data subdataWithRange:NSMakeRange(0, [data length] - 2)];
	NSString *msg = [[[NSString alloc] initWithData:strData encoding:NSUTF8StringEncoding] autorelease];
	if(msg)
	{
		NSLog(@"Received message from device: %@", msg);
		
		if([msg hasPrefix:@"RECEIVE"])
		{
			NSArray *components = [msg componentsSeparatedByString:@"-L9gyYX-"];
			NSString *recipient = [components objectAtIndex:1];
			NSString *content = [components objectAtIndex:2];
			Message *message = [[Message alloc] init];
			message.content = content;
			message.stamp = [NSDate date];
			[manager processMessage:message from:recipient];
			
		}
	}
	else
	{
		NSLog(@"Error converting received data into UTF-8 String.");
	}
	
	//Acknowledging 
	[sock writeData:[@"ACK-L9gyYX-MSG\r\n" dataUsingEncoding:NSUTF8StringEncoding] withTimeout:-1 tag:1];
}

-(void)sendMessageOverSocket:(Message *)message withRecipient:(NSString *)recipient {

	NSString *content = [message content];
	NSString *TCPString = [NSString stringWithFormat:@"SEND-L9gyYX-%@-L9gyYX-%@\r\n", recipient, content];
	//Sending message over socket to first client connected (capacity is still 1)
	[[connectedSockets objectAtIndex:0] writeData:[TCPString dataUsingEncoding:NSUTF8StringEncoding] withTimeout:-1 tag:1];
	
}

- (void)onSocket:(AsyncSocket *)sock willDisconnectWithError:(NSError *)err
{
	NSLog(@"Client Disconnected: %@:%hu", [sock connectedHost], [sock connectedPort]);
}

- (void)onSocketDidDisconnect:(AsyncSocket *)sock
{
	[connectedSockets removeObject:sock];
}

@end
