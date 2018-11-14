#import "FbSharePlugin.h"
#import <fb_share_plugin/fb_share_plugin-Swift.h>

@implementation FbSharePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFbSharePlugin registerWithRegistrar:registrar];
}
@end
