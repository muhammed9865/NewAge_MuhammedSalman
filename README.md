# NewAge_MuhammedSalman

# About
This application is used to calculate your Body Mass Index (BMI) given your weight, height and gender.

# Libraries
- Hilt
- Material Design
- Google Mobile Ads
- Navigation Component
- Lifecycle Components (ViewModel)

# Features
- Select the Metrics
- Share the result as screenshot
- Mobile Ads
- Rate application

# Obstacles faced me and solutions
- __Centering the metric item in the RecyclerView and set it as the selected metric__
The solution was to set a padding vertically, but it has to be a dynamic padding that means, the padding must be the item height (Can't be static in xml).
So I found out that creating a class of LinearLayoutManager to control the recyclerview rendering, you can then access the item height in "measureChildWithMargins".

- __Sharing a screenshot of the BMI result with other applications while saving it in cache storage (internal), not the external storage (shared).__
The solution was to use [FileProvider](https://developer.android.com/reference/androidx/core/content/FileProvider) which is used to provide a Uri that's accessible by other applications
who have a permission to access this Uri. Sending the Uri provided by FileProvider using the [Intent](https://developer.android.com/reference/android/content/Intent) and adding the [Grant read permission](https://developer.android.com/reference/android/content/Intent#FLAG_GRANT_READ_URI_PERMISSION) flag

# Project Notes
- Loading ads using AdLoader doesn't work on Android Studio Emulators but works fine on real devices (Tested)
- If there isn't an ad available to be displayed, the NativeAdView' visibility is gone (in gallery section)


# Gallery
<span>
<img src="https://user-images.githubusercontent.com/84887514/210598455-aaf79aec-d9d6-4c10-bdc0-2e9b842da239.png" alt="mindmap" height="700" width="350"/>
<img src="https://user-images.githubusercontent.com/84887514/210598480-b511fef0-aa41-46ee-9e42-1c4c265b7b7f.png" alt="drawing" height="700" width="350"/> 
<img src="https://user-images.githubusercontent.com/84887514/210598486-f0dbf436-7230-4df2-8bca-6a66c78d68f6.png" alt="drawing" height="700" width="350"/>
</span>
