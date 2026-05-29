extern crate jni;

use std::ffi::CString;
use std::os::raw::c_char;

use jni::JNIEnv;
use jni::objects::{JClass, JObject, JValue};

use rand::Rng;

pub type Callback = unsafe extern "C" fn(*const c_char) -> ();

#[unsafe(no_mangle)]
#[allow(non_snake_case)]
pub extern "C" fn invokeCallbackViaJNA(callback: Callback) {
    //let s = rand::thread_rng().gen_range(1..=100);
    //let s = s.to_string();
    //let s = CString::new("Hello from first Rust JNA").unwrap();
    let s = secondrust::get_greeting();
    unsafe { callback(s.as_ptr()); }
}

#[unsafe(no_mangle)]
#[allow(non_snake_case)]
pub extern "C" fn Java_com_devwindsw_linkrust_MainActivity_invokeCallbackViaJNI(
    env: JNIEnv,
    _class: JClass,
    callback: JObject
) {
    let s = String::from("Hello from first Rust JNI");
    let response = env.new_string(&s)
        .expect("Couldn't create java string!");
    env.call_method(callback, "callback", "(Ljava/lang/String;)V",
                    &[JValue::from(JObject::from(response))]).unwrap();
}
