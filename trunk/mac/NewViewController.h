//
//  NewViewController.h
//  SMS Tether
//
//  http://code.google.com/p/sms-tether
//

#import <Cocoa/Cocoa.h>
#import "MAAttachedWindow.h"
#import "ConversationManager.h"


@interface NewViewController : NSObject {

	IBOutlet NSView *view;
	BOOL windowShowing;
	MAAttachedWindow *window;
	IBOutlet NSWindow *mainWindow;
	IBOutlet NSTextField *numberField;
	
	IBOutlet ConversationManager *manager;
	IBOutlet NSTableView *tableView;
}

-(IBAction)showWindow:(id)sender;
-(IBAction)createConversation:(id)sender;

@end
