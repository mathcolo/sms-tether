//
//  ConversationManager.h
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
