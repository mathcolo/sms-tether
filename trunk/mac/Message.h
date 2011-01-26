//
//  Message.h
//  SMS Tether
//
//  http://code.google.com/p/sms-tether
//

#import <Cocoa/Cocoa.h>


@interface Message : NSObject {

	NSString *content;
	BOOL direction;
	NSDate *stamp;
	
	
}

@property (nonatomic, retain) NSDate *stamp;
@property (nonatomic, copy) NSString *content;
@property (nonatomic, assign) BOOL direction;
 
@end
