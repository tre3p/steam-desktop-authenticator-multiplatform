use std::collections::HashMap;
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
        let dir_mafiles = utils::file_utils::list_dir_files_by_extension(mafiles_dir_path, ".maFile");
        let mafiles_parsed = utils::mafile_utils::parse_mafiles(dir_mafiles);
        let name_to_secret_map = utils::mafile_utils::convert_mafiles_to_map(&mafiles_parsed);

        Self {
            dir_path: String::from(mafiles_dir_path),
            mafiles: mafiles_parsed,
            name_to_secret: name_to_secret_map
        }
    }

    // TODO: copy_to_dir, get_secret_by_name...
}