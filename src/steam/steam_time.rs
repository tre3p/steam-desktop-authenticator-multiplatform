use chrono::Utc;
use reqwest::Method;
use crate::utils::http_utils;

/**
URL to retrieve Steam server time from
**/
const STEAM_TIME_API_URL: &str = "https://api.steampowered.com/ITwoFactorService/QueryTime/v0001?steamid=0";

const STEAM_RESPONSE_JSON: &str = "response";

const STEAM_SERVER_TIME_JSON: &str = "server_time";

/**
Struct which helpful in getting current time on Steam server and getting current steam time chunk
 **/
pub struct SteamTime {
    aligned: bool,
    time_diff: i64,
}

impl SteamTime {

    /**
    Creates instance of SteamTime objects.
    **/
    pub fn new() -> Self {
        Self {
            aligned: false,
            time_diff: 0
        }
    }

    /**
    Returns current time on Steam server
    **/
    pub fn get_steam_time(&mut self) -> Result<i64, &str> {
        if !self.aligned {
            let time_diff = self.align_steam_time().unwrap();
            self.time_diff = time_diff;
            self.aligned = true;
        }

        Ok(Utc::now().timestamp() + self.time_diff)
    }

    /**
    Calculates current steam chunk to know when auth codes need to be refresh
     **/
    pub fn get_current_steam_chunk(&mut self) -> Result<i64, &str> {
        let current_steam_time = self.get_steam_time().unwrap();
        let current_steam_chunk = current_steam_time / 30;
        let seconds_until_change = current_steam_time - (current_steam_chunk * 30);

        Ok(30 - seconds_until_change)
    }

    /**
    Aligns self time difference between local time and Steam server time
    **/
    fn align_steam_time(&self) -> Result<i64, String> {
        let current_time = Utc::now().timestamp();
        let steam_resp = http_utils::execute_blank_http_request(STEAM_TIME_API_URL, Method::POST);

        match steam_resp {
            Ok(json) => {
                let steam_server_time = &json[STEAM_RESPONSE_JSON][STEAM_SERVER_TIME_JSON];
                let steam_server_time = steam_server_time.as_str().unwrap();

                let time_diff: i64 = steam_server_time.trim().parse::<i64>().unwrap() - current_time;
                Ok(time_diff)
            }
            Err(err) => {
                println!("Error while parsing time_diff: {}", err);
                Err(err.to_string())
            }
        }
    }
}