//
//  Conversation.m
//  SMS Tether
//
//  http://code.google.com/p/sms-tether
//

#import "Conversation.h"

@implementation Conversation
@synthesize messages, contact;

- (id)init
{

	if(self = [super init])
	{
		messages = [NSMutableArray array];
		[messages retain];
	}
	return self;
}


@end
