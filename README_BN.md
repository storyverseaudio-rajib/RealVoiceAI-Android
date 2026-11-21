# RealVoice AI — Android Starter (v0.1)

এটি RealVoice AI-এর প্রথম Android UI prototype source project।

## এই ভার্সনে যা আছে
- Audio file picker (Android document picker)
- Target voice selector: Male, Female, Boy, Girl, Old Man, Old Woman, Monster, Robot
- Emotion selector: Preserve, Happy, Sad, Angry, Fear, Romantic, Confident, Crying, Laughing, Sleepy
- Offline / Online mode selector
- 10–30 মিনিটের long-audio workflow-এর UI placeholder

## গুরুত্বপূর্ণ সীমাবদ্ধতা
এই v0.1 হলো UI prototype। এখানে এখনো RVC/ONNX voice conversion engine যুক্ত করা হয়নি। Convert Voice চাপলে prototype status দেখাবে; এটি এখনো voice বদলায় না।

## Build
- Android Studio / compatible Android build environment
- JDK 17
- Android SDK 35
- Gradle 8.9+ (wrapper add করতে হবে)
- Minimum Android: 12 (API 31)

প্রজেক্টটি ফোনে Code editor দিয়ে দেখা/সম্পাদনা করা যায়। APK build করতে Android SDK ও Gradle build environment প্রয়োজন; শুধু ZIP খুললেই APK তৈরি হবে না।

## পরবর্তী ধাপ
1. RVC-Android pipeline-এর compatible ONNX model integration
2. Audio decode/resample ও chunk manager
3. Background processing, progress, cancellation ও output stitching
4. Emotion conversion pipeline (RVC একা emotion synthesis করে না)
5. Online inference backend — free GPU availability guaranteed নয়

## Safety / voice rights
শুধু নিজের কণ্ঠ বা অনুমতি পাওয়া কণ্ঠের model ব্যবহার করুন। পরিচিত ব্যক্তির কণ্ঠ নকল করে প্রতারণা বা পরিচয় জালিয়াতি করবেন না।
