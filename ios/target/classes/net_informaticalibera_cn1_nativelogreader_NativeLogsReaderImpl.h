#import <Foundation/Foundation.h>

@interface net_informaticalibera_cn1_nativelogreader_NativeLogsReaderImpl : NSObject {
}

-(NSString*)readLog;
-(void)clearAndRestartLog;
-(void)clearLog;
-(BOOL)isSupported;
@end
