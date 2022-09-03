package steam

import (
	"crypto/hmac"
	"crypto/sha1"
	"encoding/base64"
	"encoding/binary"
	"fmt"
)

var (
	chars    = "23456789BCDFGHJKMNPQRTVWXY"
	charsLen = uint32(len(chars))
)

func GetTwoFactor(loginToShared *map[string]string) map[string]string {
	currentSteamTime := GetSteamTime()
	result := make(map[string]string)

	for login, shared := range *loginToShared {
		data, err := base64.StdEncoding.DecodeString(shared)
		if err != nil {
			fmt.Printf("[TwoFactor] Error while decoding shared secret: %v", err.Error())
			panic(err)
		}

		ful := make([]byte, 8)
		binary.BigEndian.PutUint32(ful[4:], uint32(currentSteamTime/30))

		hmac := hmac.New(sha1.New, data)
		hmac.Write(ful)

		sum := hmac.Sum(nil)
		start := sum[19] & 0x0F
		slice := binary.BigEndian.Uint32(sum[start:start+4]) & 0x7FFFFFFF

		buf := make([]byte, 5)
		for i := 0; i < 5; i++ {
			buf[i] = chars[slice%charsLen]
			slice /= charsLen
		}
		result[login] = string(buf)
	}

	return result
}
