use std::path::Path;
use std::{fs, path};
use std::io::Result;

pub fn create_dir_if_not_exists(path: &str) {
    let folder_path = Path::new(path);

    if !folder_path.exists() {
        let is_created = fs::create_dir(folder_path);
        match is_created {
            Ok(_) => {
                println!("Folder {} successfully created!", path);
                return;
            }
            Err(e) => {
                println!("Error while creating folder {}: {}", path, e.to_string());
                return;
            }
        }
    }

    println!("Folder {} already exists", path);
}

pub fn list_dir_files_by_extension(path: &str, ext: &str) -> Vec<String> {
    let mut filtered_files: Vec<String> = Vec::new();
    let all_dir_files = fs::read_dir(path).unwrap();

    for file in all_dir_files {
        if let Some(file_name) = file.unwrap().file_name().to_str() {
            if file_name.ends_with(ext) {
                filtered_files.push(String::from(path) + file_name);
            }
        }
    }

    filtered_files
}

pub fn read_file(file_path: &str) -> Result<String> {
    fs::read_to_string(file_path)
}