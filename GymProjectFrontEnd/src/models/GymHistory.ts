class GymHistory {
    id       ?:        number;
    exerciseId       ?:        number;
    body_part?: string;
    exercise1?: string;
    exercise2?: string;
    exercise3?: string;
    exercise4?: string;
    exercise5?: string;
    date?: string;




    constructor(

        id?: number
        , body_part?: string
        , exercise1?: string
        , exercise2?: string
        , exercise3?: string
        , exercise4?: string
        , exercise5?: string
        ,date?: string
        ,exerciseId       ?:        number


    ) {
        this.id = id
        this.exerciseId = exerciseId
        this.body_part = body_part
        this.exercise1 = exercise1
        this.exercise2 = exercise2
        this.exercise3 = exercise3
        this.exercise4 = exercise4
        this.exercise5 = exercise5
        this.date=date


    }
}

export default GymHistory;