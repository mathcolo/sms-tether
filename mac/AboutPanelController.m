//
//  AboutPanelController.m
//  SMS Tether
//
//  http://code.google.com/p/sms-tether
//

#import "AboutPanelController.h"


@implementation AboutPanelController

-(void)awakeFromNib {
	
	NSBundle *bundle = [NSBundle mainBundle];
	NSDictionary *infoDict = [bundle infoDictionary];
	[versionField setStringValue:[infoDict valueForKey:@"CFBundleShortVersionString"]];
	
}

@end
