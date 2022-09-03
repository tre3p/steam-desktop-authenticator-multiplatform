package structs

type TimeQuery struct {
	Response struct {
		ServerTime                        string `json:"server_time"`
		SkewToleranceSeconds              string `json:"skew_tolerance_seconds"`
		LargeTimeJink                     string `json:"large_time_jink"`
		ProbeFrequencySeconds             int    `json:"probe_frequency_seconds"`
		AdjustedTimeProbeFrequencySeconds int    `json:"adjusted_time_probe_frequency_seconds"`
		HintProbeFrequencySeconds         int    `json:"hint_probe_frequency_seconds"`
		SyncTimeout                       int    `json:"sync_timeout"`
		TryAgainSeconds                   int    `json:"try_again_seconds"`
		MaxAttempts                       int    `json:"max_attempts"`
	} `json:"response"`
}
