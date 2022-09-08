package steam

import "sda-multiplatform/structs"

var (
	AccountNameToKey          = make(map[string]string)
	AccountNameToSharedSecret = make(map[string]string)
	AccountNames              []string
)

func InitStorage(data *[]structs.MaFile) {
	for _, elem := range *data {
		accountName := elem.AccountName.(string)
		sharedSecret := elem.SharedSecret.(string)

		AccountNameToSharedSecret[accountName] = sharedSecret
		AccountNameToKey[accountName] = GetTwoFactor(sharedSecret)
		AccountNames = append(AccountNames, accountName)
	}
}

func RefreshLoginKeys() {
	for login, sharedSecret := range AccountNameToSharedSecret {
		AccountNameToKey[login] = GetTwoFactor(sharedSecret)
	}
}

func GetLoginKey(login string) string {
	return AccountNameToKey[login]
}

func AccountNamesContains(target string) bool {
	for _, e := range AccountNames {
		if e == target {
			return true
		}
	}
	return false
}
