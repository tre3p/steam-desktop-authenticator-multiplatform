use std::collections::HashMap;
use std::hash::Hash;
use serde_json::Value;
use crate::utils::file_utils;
use crate::steam::models::MaFile;

pub fn parse_mafiles(paths: Vec<String>) -> Vec<MaFile> {
    let mut parsed_mafiles: Vec<MaFile> = Vec::new();

    for path in paths {
        let mafile_content = file_utils::read_file(&path);
        match mafile_content {
            Ok(content) => {
                let mafile_json: Value = serde_json::from_str(&content).unwrap();
                parsed_mafiles.push(MaFile {
                    account_name: String::from(mafile_json["account_name"].as_str().unwrap()),
                    shared_secret: String::from(mafile_json["shared_secret"].as_str().unwrap())
                })
            }
            Err(e) => {
                println!("Error while reading maFile content. {}", e.to_string());
                continue;
            }
        }
    }

    parsed_mafiles
}

pub fn convert_mafiles_to_map(mafiles: &Vec<MaFile>) -> HashMap<String, String> {
    let mut result_map: HashMap<String, String> = HashMap::new();

    for mafile in mafiles {
        result_map.insert(mafile.account_name.clone(), mafile.shared_secret.clone());
    }

    result_map
}