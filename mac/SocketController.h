//
//  SocketController.h
//  SMS Tether
//
//  http://code.google.com/p/sms-tether
//

#import <Cocoa/Cocoa.h>
#import "Message.h"

@class ConversationManager;
@class AsyncSocket;

@interface SocketController : NSObject <NSApplicationDelegate> {

	AsyncSocket *listenSocket;
	NSMutableArray *connectedSockets;
	
	BOOL isRunning;
	
	IBOutlet ConversationManager *manager;
	IBOutlet NSWindow *mainWindow;
	
	IBOutlet NSButton *sendButton;
	
}

-(void)startSocket;
-(IBAction)startSocketServer:(id)sender;
-(void)sendMessageOverSocket:(Message *)message withRecipient:(NSString *)recipient;

@end
