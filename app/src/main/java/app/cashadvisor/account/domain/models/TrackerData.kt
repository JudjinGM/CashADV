package app.cashadvisor.account.domain.models

data class TrackerData (
    val trackingState: TrackingStateData,
    val goals: List<GoalData>,
    val finHealth: FinHealthData,
)