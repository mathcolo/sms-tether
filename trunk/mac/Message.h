//
//  Message.h
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


@interface Message : NSObject {

	NSString *content;
	BOOL direction;
	NSDate *stamp;
	
	
}

@property (nonatomic, retain) NSDate *stamp;
@property (nonatomic, copy) NSString *content;
@property (nonatomic, assign) BOOL direction;
 
@end
