//
//  Contact.h
//  SMS Tether
//
//  http://code.google.com/p/sms-tether
//

#import <Cocoa/Cocoa.h>


@interface Contact : NSObject {

	NSString *number;
	
}

@property (nonatomic, copy) NSString *number;
@end
