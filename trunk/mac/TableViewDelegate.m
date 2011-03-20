//
//  TableViewDelegate.m
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

#import "TableViewDelegate.h"
#import "ConversationManager.h"
#import "Conversation.h"
#import "Message.h"

@implementation TableViewDelegate

- (int)numberOfRowsInTableView:(NSTableView *)tableView
{
    return [manager.conversations count];
}

- (id)tableView:(NSTableView *)tableView objectValueForTableColumn:(NSTableColumn *)tableColumn row:(int)row
{
	NSString *number = [[[manager.conversations objectAtIndex:row] contact] number];
	if([tableView selectedRow] > -1)[self tableViewSelectionDidChange:nil]; //Strange workaround in order for the text view to be updated with new messages automatically
	return number;
}

- (void)tableViewSelectionDidChange:(NSNotification *)notification
{
    int row;
    row = [tableView selectedRow];
	
	if(row == -1)
	{
		[textView setString:@""];
		[sendButton setEnabled:NO];
		return;
	}

	[sendButton setEnabled:YES];
	
	NSString *appender = @"";
	
	//NSLog(@"%@", [manager.conversations description]);
	Conversation *c = [manager.conversations objectAtIndex:row];

	NSMutableArray *messages = c.messages;
	Contact *contact = c.contact;
	NSString *number = contact.number;

	NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
	
	for(int i = 0; i < [messages count]; i++)
	{
		Message *arrayMessage = [messages objectAtIndex:i];
		NSString *content = arrayMessage.content;
		//NSLog(@"tableViewDelegate: content = %@", content);
		BOOL direction = arrayMessage.direction;

		NSString *header;
		
		if(direction)header = @"Me";
		else header = number;
		
		//NSLog(@"tableViewDelegate selectionDidChange: header = %@", header);
		
		[formatter setFormatterBehavior:NSDateFormatterBehavior10_4]; //Not really sure about the 10.4 behavior...
		[formatter setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
		NSString *date = [formatter stringFromDate:arrayMessage.stamp];
		appender = [appender stringByAppendingString:[NSString stringWithFormat:@"\n<%@> %@: %@", date, header, content]];
		
	}
	[textView setString:appender];
	[formatter release];
}

@end
