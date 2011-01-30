//
//  NewViewController.m
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
