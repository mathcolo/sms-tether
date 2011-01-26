//
//  ConversationManager.h
//  SMS Tether
//
//  http://code.google.com/p/sms-tether
//

#import <Cocoa/Cocoa.h>
#import "Conversation.h"
#import "SocketController.h"

@class Message;

@interface ConversationManager : NSObject {

	NSMutableArray *conversations;
	IBOutlet NSTextView *textView;
	IBOutlet NSTextField *inputField;
	IBOutlet NSTableView *tableView;
	IBOutlet SocketController *socketController;
}

-(void)processMessage:(Message*)message from:(NSString*)number;

-(IBAction)initSend:(id)sender;

@property (nonatomic, retain) NSMutableArray *conversations;

@end
