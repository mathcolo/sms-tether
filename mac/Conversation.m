//
//  Conversation.m
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

-(void)encodeWithCoder:(NSCoder *)coder
{
	[coder encodeObject:messages forKey:@"messages"];
	[coder encodeObject:contact forKey:@"contact"];
}

-(id)initWithCoder:(NSCoder *)coder
{
	messages = [[coder decodeObjectForKey:@"messages"] retain];
	contact = [[coder decodeObjectForKey:@"contact"] retain];
	return self;
}


@end
