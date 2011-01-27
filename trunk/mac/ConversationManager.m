//
//  ConversationManager.m
//  SMS Tether
//
//  http://code.google.com/p/sms-tether
//
//This file is part of SMS Tether.
//
//SMS Tether is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//SMS Tether is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.

//You should have received a copy of the GNU General Public License
//along with SMS Tether.  If not, see <http://www.gnu.org/licenses/>.

#import "ConversationManager.h"
#import "Message.h"

@implementation ConversationManager

@synthesize conversations;

- (id)init
{
	
	if(self = [super init])
	{
		conversations = [NSMutableArray array];
		[conversations retain];
		
	}
	return self;
}

-(void)processMessage:(Message*)message from:(NSString*)number {
	
	//find the conversation in the array conversations where the number matches
	Conversation *c = nil;
	
	for(int i=0; i < [conversations count]; i++)
	{
		Conversation *search = [conversations objectAtIndex:i];
		NSLog(@"[[[search contact] number] is %@", [[search contact] number]);
		if([[[search contact] number] hasPrefix:number])
		{
			c = search;
		}
	}
	
	BOOL flag = FALSE;
	
	if(c == nil)
	{
		Conversation *newConversation = [[Conversation alloc] init];
		[newConversation retain];
		Contact *sender = [[Contact alloc] init];
		[sender retain];
		
		[sender setNumber:number];		 
		[newConversation setContact:sender];
		c = newConversation;
		flag = TRUE;
	}
	
	[[c messages] addObject:message];
	if(flag)[conversations addObject:c];
	
	[tableView reloadData];
	
}

-(NSMutableArray *)conversations {

	return [conversations retain];
}

-(IBAction)initSend:(id)sender {
	
	NSString *content = [inputField stringValue];
	NSLog(@"initSend: content = %@", content);
	int sc = [tableView selectedRow];
	NSLog(@"initSend: sc = %d", sc);
	
	Conversation *selected = [conversations objectAtIndex:sc];
	
	Message *toSend = [[Message alloc] init];
	toSend.content = content;
	toSend.direction = YES;
	toSend.stamp = [NSDate date];
	
	NSMutableArray *conversationMessages = [selected messages];
	[conversationMessages addObject:toSend];
	NSLog(@"initSend: conversationMessages = %@", [conversationMessages description]);
	
	NSString *recipient = [[selected contact] number];
	[socketController sendMessageOverSocket:toSend withRecipient:recipient];
	[tableView reloadData];
	[[tableView delegate] tableViewSelectionDidChange:nil]; //A strange workaround in order for the GUI elements in the main window to reload and display the new message
	
	[inputField setStringValue:@""]; //Reset the message entry field
	
}

@end
