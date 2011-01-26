//
//  TableViewDelegate.m
//  SMS Tether
//
//  http://code.google.com/p/sms-tether
//

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

	[textView setString:@""];
	
	NSLog(@"%@", [manager.conversations description]);
	Conversation *c = [manager.conversations objectAtIndex:row];

	NSMutableArray *messages = c.messages;
	Contact *contact = c.contact;
	NSString *number = contact.number;

	for(int i = 0; i < [messages count]; i++)
	{
		Message *arrayMessage = [messages objectAtIndex:i];
		NSString *content = arrayMessage.content;
		//NSLog(@"tableViewDelegate: content = %@", content);
		BOOL direction = arrayMessage.direction;

		NSString *currentContent = [textView string];
		NSString *header;
		
		if(direction)
		{
			header = @"Me";
		}
		else {
			header = number;
		}
		
		//NSLog(@"tableViewDelegate selectionDidChange: header = %@", header);
		
		NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
		[formatter setFormatterBehavior:NSDateFormatterBehavior10_4]; //Not really sure about the 10.4 behavior...
		[formatter setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
		NSString *date = [formatter stringFromDate:arrayMessage.stamp];
		NSString *newString = [currentContent stringByAppendingString:[NSString stringWithFormat:@"\n<%@> %@: %@", date, header, content]];
		[textView setString:newString];
	}
}

@end
