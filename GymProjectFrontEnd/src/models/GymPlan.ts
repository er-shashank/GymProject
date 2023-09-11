import GymPlanPrimaryKey from "./GymPlanPrimaryKey";

class GymPlan {
    gymPlanPrimaryKey?: GymPlanPrimaryKey;
    body_part?: string;
    exercise1?: string;
    exercise2?: string;
    exercise3?: string;
    exercise4?: string;
    exercise5?: string;

    constructor(

        gymPlanPrimaryKey?: GymPlanPrimaryKey
        , body_part?: string
        , exercise1?: string
        , exercise2?: string
        , exercise3?: string
        , exercise4?: string
        , exercise5?: string


    ) {
        this.gymPlanPrimaryKey = gymPlanPrimaryKey
        this.body_part = body_part
        this.exercise1 = exercise1
        this.exercise2 = exercise2
        this.exercise3 = exercise3
        this.exercise4 = exercise4
        this.exercise5 = exercise5


    }
}

export default GymPlan;