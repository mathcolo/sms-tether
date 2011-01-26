//
//  TableViewDelegate.h
//  SMS Tether
//
//  http://code.google.com/p/sms-tether
//

#import <Cocoa/Cocoa.h>
#import "ConversationManager.h"


@interface TableViewDelegate : NSObject <NSTableViewDelegate> {
	
	IBOutlet NSTableView *tableView;
	IBOutlet NSScrollView *scrollView;
	
	IBOutlet ConversationManager *manager;
	IBOutlet NSTextView *textView;
	
	IBOutlet NSButton *sendButton;
}

@end
