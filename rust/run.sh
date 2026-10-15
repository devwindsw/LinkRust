#!/usr/bin/bash

cargo ndk -t arm64-v8a build --release

cp ./target/aarch64-linux-android/release/librootrust.so ../app/libs/arm64-v8a/
