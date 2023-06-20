use std::time::Duration;
use reqwest::blocking::Client;
use reqwest::{Method, Error};
use reqwest::header::{HeaderMap, CONTENT_LENGTH, HeaderValue};
use serde_json::Value;

pub fn execute_blank_http_request(url: &str, verb: Method) -> Result<Value, Error> {
    let client = create_default_client();

    let req = client
        .request(verb, url)
        .timeout(Duration::from_secs(5))
        .build()?;

    let res = client
        .execute(req)?
        .text()?;

    let response_json: Value = serde_json::from_str(&res).unwrap();
    Ok(response_json)
}

fn create_default_client() -> Client {
    let mut headers = HeaderMap::new();
    headers.insert(CONTENT_LENGTH, HeaderValue::from_static("0"));

    let client = Client::builder()
        .default_headers(headers)
        .build()
        .expect("Failed to create client.");

    client
}