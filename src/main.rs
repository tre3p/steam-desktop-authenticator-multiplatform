mod steam;
mod utils;

fn main() {
    let mut steam_dir = steam::models::MaFileDir::new("maFiles/");
    steam_dir.copy_to_dir("/Users/noname/Desktop/maFiles/");
}
