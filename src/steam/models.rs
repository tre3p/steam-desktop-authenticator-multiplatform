use std::collections::HashMap;
use std::fs;
use std::path::Path;
use crate::utils;

/**
Structs represents part of .maFile content.
Hold "account_name" and "shared_secret" values from .maFile.
**/
#[derive(Debug)]
pub struct MaFile {
    pub account_name: String,
    pub shared_secret: String
}

/**
Struct represents root maFiles directory
**/
pub struct MaFileDir {
    dir_path: String,
    mafiles: Vec<MaFile>,
    name_to_secret: HashMap<String, String>
}

impl MaFileDir {
    pub fn new(mafiles_dir_path: &str) -> Self {
        utils::file_utils::create_dir_if_not_exists(mafiles_dir_path);

        let dir_mafiles = utils::file_utils::list_files_by_extension(mafiles_dir_path, ".maFile");
        let mafiles_parsed = utils::mafile_utils::parse_mafiles(dir_mafiles);
        let name_to_secret_map = utils::mafile_utils::convert_mafiles_to_map(&mafiles_parsed);

        Self {
            dir_path: String::from(mafiles_dir_path),
            mafiles: mafiles_parsed,
            name_to_secret: name_to_secret_map
        }
    }

    // TODO: copy_to_dir, get_secret_by_name...
    pub fn copy_to_dir(&mut self, path: &str) {
        let mafiles_list = utils::file_utils::list_files_by_extension(path, ".maFile");

        for mafile_path in &mafiles_list {
            let mafile_parsed = utils::mafile_utils::parse_mafiles(vec![mafile_path.to_string()]);
            let mafile = &mafile_parsed[0];

            match self.name_to_secret.get(&mafile.account_name) {
                Some(_) => {
                    println!("Account already exists, skipping...");
                    continue;
                }
                None => {
                    println!("Account does not exists, handling...");

                    let mut copied_mafile_path = String::from(&self.dir_path);
                    copied_mafile_path.push_str(&mafile.account_name.as_str());
                    copied_mafile_path.push_str(".maFile");

                    fs::copy(&mafile_path, copied_mafile_path)
                        .expect("Unable to copy maFile to software dir.");

                    // TODO: insert 'mafile' variable to self.mafiles and to name_to_secret map
                }
            }
        }
    }
}