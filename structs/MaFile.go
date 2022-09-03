package structs

type MaFile struct {
	SharedSecret   interface{} `json:"shared_secret"`
	SerialNumber   interface{} `json:"serial_number"`
	RevocationCode interface{} `json:"revocation_code"`
	URI            interface{} `json:"uri"`
	ServerTime     interface{} `json:"server_time"`
	AccountName    interface{} `json:"account_name"`
	TokenGid       interface{} `json:"token_gid"`
	IdentitySecret interface{} `json:"identity_secret"`
	Secret1        interface{} `json:"secret_1"`
	Status         interface{} `json:"status"`
	DeviceID       interface{} `json:"device_id"`
	FullyEnrolled  interface{} `json:"fully_enrolled"`
	Session        struct {
		SessionID        interface{} `json:"SessionID"`
		SteamLogin       interface{} `json:"SteamLogin"`
		SteamLoginSecure interface{} `json:"SteamLoginSecure"`
		WebCookie        interface{} `json:"WebCookie"`
		OAuthToken       interface{} `json:"OAuthToken"`
		SteamID          interface{} `json:"SteamID"`
	} `json:"Session"`
}
