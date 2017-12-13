# FastScrollList
A demo application for fast scroll feature of RecyclerView.

## Approaches
This demo consists of 2 approaches
1. The first approach is ported from a library called [LollipopContactsRecyclerViewFastScroller](https://github.com/AndroidDeveloperLB/LollipopContactsRecyclerViewFastScroller). It is simple, lightweight but lacks customization capabilities. More problematically, the xml has to declared one more view beside RecyclerView to achieve the purpose, it could be a little troublesome to modify such code in large codebase.
2. The second approach is a combination of the above-mentioned library and another library called [RecyclerView-FastScroll](https://github.com/timusus/RecyclerView-FastScroll). It not only keeps the simplicity of the original lib but also gains the ability of declaring only one view in xml layout. A considerable drawback is the fact that the drawing process, the bubble for instance, is actually hardcoded and difficult to change.

## Performance
Testing with [Profile GPU Rendering](https://developer.android.com/topic/performance/rendering/profile-gpu.html) enabled, the demo application rendering time is slightly exceed the 16ms standard (to maintain 60 FPS). In real life situation, however, it is still acceptable for most users, without any significant lags. In terms of comparing to other major commercial products, it is somewhat better than the Facebook Messenger, but is far behind the default Contact app in Android.

## Note
Keep in mind that this is only a Proof of Concept, not a usable library.
