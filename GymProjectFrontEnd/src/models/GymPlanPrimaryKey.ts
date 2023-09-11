class GymPlanPrimaryKey {
    userId?: number;
    exerciseId?: number;

    constructor(
        userId?: number
        , exerciseId?: number
    ) {
        this.userId = userId
        this.exerciseId = exerciseId
    }
}

export default GymPlanPrimaryKey;