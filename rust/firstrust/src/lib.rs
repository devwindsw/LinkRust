use std::os::raw::c_char;

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
