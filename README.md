![Asclepius' logo and home screen](/assets-github/header.png)

# Asclepius
Asclepius is a skin cancer detection app, implemented with pre-trained TensorFlow Lite machine learning models for efficient on-device skin cancer detection.

**Download the APK from below**
[![Asclepius](https://img.shields.io/badge/Download-Asclepius-<COLOR>.svg?style=for-the-badge&logo=android)](https://github.com/kevinjeremia/Asclepius/raw/master/assets-github/Asclepius.apk)


## Build on your environment :hammer:
- Add **https://newsapi.org/** as BASE_URL to **local.properties** file.
- Add your NewsAPI API key to **local.properties** file. If you don't have have the API Key yet, get it from [newsapi.org](https://newsapi.org/)
- ```xml
BASE_URL = https://newsapi.org/
API_KEY = YOUR_API_KEY
```


## Libraries and APIs Used :books:
* [News API][0] A public HTTP REST API for searching and retrieving live articles from all over the web.
* [TensorFlow Lite][1] A library that uses TensorFlow models that are converted into a smaller, portable, more efficient machine learning model format.
* [SplashScreen API][2] An API that provides a standard way for apps to implement smooth and attractive app launch animations.
* [Navigation][3] A framework for navigating between 'destinations' within an Android application that provides a consistent API whether destinations are implemented as Fragments, Activities, or other components.
* [CircleImageView][4] A fast circular ImageView perfect for profile images by hdodenhof.
* [Glide][5] A fast and efficient open source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.
* [Recyclerview] [6] An API under Jetpack library that displays large sets of data in your UI while minimizing memory usage.
* [Retrofit] [7] A library that makes it easier to consume RESTful web services by providing a simple and efficient way to handle HTTP requests and responses.
* [Room] [8] A library that provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite.
* [uCrop] [9] A convenient open-source image cropping library for Android that includes basic image editing features like cropping, rotating, compressing, and scaling.

[0]:  https://newsapi.org/
[1]:  https://www.tensorflow.org/lite/android
[2]:  https://developer.android.com/develop/ui/views/launch/splash-screen
[3]:  https://developer.android.com/jetpack/androidx/releases/navigation
[4]:  https://github.com/hdodenhof/CircleImageView
[5]:  https://github.com/bumptech/glide
[6]:  https://developer.android.com/jetpack/androidx/releases/recyclerview
[7]:  https://github.com/square/retrofit
[8]:  https://developer.android.com/jetpack/androidx/releases/room
[9]:  https://github.com/Yalantis/uCrop


## License :oncoming_police_car:
```
MIT License

Copyright (c) 2024 Kevin Jeremia

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.```
