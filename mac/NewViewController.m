//
//  NewViewController.m
//  SMS Tether
//
//  http://code.google.com/p/sms-tether
//

#import "NewViewController.h"
#import "Message.h"
#import "Conversation.h"


@implementation NewViewController

-(void)awakeFromNib {
	windowShowing = NO;
}

-(IBAction)showWindow:(id)sender {
	
	if(!windowShowing)
	{
		windowShowing = YES;
		NSPoint point = [mainWindow mouseLocationOutsideOfEventStream];
		
		window = [[MAAttachedWindow alloc] initWithView:view attachedToPoint:point inWindow:mainWindow onSide:MAPositionRight atDistance:20.0];
		[mainWindow addChildWindow:window ordered:NSWindowAbove];
		
	}
	else {
		windowShowing = NO;
		[mainWindow removeChildWindow:window];
		[window orderOut:self];
		[window release];
		window = nil;
		
	}
}

-(IBAction)createConversation:(id)sender {
	
	NSString *number = [numberField stringValue];
	NSMutableArray *conversations = manager.conversations;
	
	Contact *contact = [[Contact alloc] init];
	contact.number = number;
	
	Conversation *c = [[Conversation alloc] init];
	c.contact = contact;
	
	[conversations addObject:c];
	[tableView reloadData];
	
	//The MAAttachedWindow-recommended method for releasing the attached window
	windowShowing = NO;
	[mainWindow removeChildWindow:window];
	[window orderOut:self];
	[window release];
	window = nil;
	
}

@end
