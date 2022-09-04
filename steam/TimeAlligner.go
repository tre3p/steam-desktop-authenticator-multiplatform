package steam

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
	"sda-multiplatform/structs"
	"strconv"
	"time"
)

var (
	alligned             = false
	timeDifference int64 = 0
)

const (
	STEAM_TIME_API = "https://api.steampowered.com/ITwoFactorService/QueryTime/v0001?steamid=0"
)

func GetSteamTime() int64 {
	if !alligned {
		alignSteamTime()
	}
	return time.Now().Unix() + timeDifference
}

func alignSteamTime() {
	currentTime := time.Now().Unix()

	resp, err := http.Post(STEAM_TIME_API, "", nil)
	if err != nil {
		fmt.Printf("[TimeAlligner] Error while sending request to Steam API Time: %v", err.Error())
		panic(err)
	}

	var timeQuery structs.TimeQuery
	bodyBytes, err2 := ioutil.ReadAll(resp.Body)
	if err2 != nil {
		fmt.Printf("[TimeAlligner] Error while parsing response from Steam API Time: %v", err2.Error())
		panic(err2)
	}

	json.Unmarshal(bodyBytes, &timeQuery)
	steamServerTimeToInt, _ := strconv.Atoi(timeQuery.Response.ServerTime)

	timeDifference = int64(steamServerTimeToInt) - currentTime
	alligned = true
}
