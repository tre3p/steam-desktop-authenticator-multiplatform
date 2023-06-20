use std::path::Path;
use std::fs;

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