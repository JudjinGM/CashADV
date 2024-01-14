package app.cashadvisor.account.domain.models.account

data class TrackerData (
    val trackingState: TrackingStateData,
    val goals: List<GoalData>,
    val finHealth: FinHealthData,
)