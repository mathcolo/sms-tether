//
//  Conversation.h
//  SMS Tether
//
//  http://code.google.com/p/sms-tether
//

#import <Cocoa/Cocoa.h>
#import "Message.h"
#import "Contact.h"


@interface Conversation : NSObject {

	Contact *contact;
	NSMutableArray *messages;
	
}

@property (nonatomic, retain) Contact *contact;
@property (nonatomic, retain) NSMutableArray *messages;

@end
