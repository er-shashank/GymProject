
import GymHistory from "../../models/GymHistory";

export const SearchBook: React.FC<{ book: GymHistory }> = (props) => {
    return (
        <div className='card mt-3 shadow p-3 mb-3 bg-body rounded'>
            <div className='row g-0'>
                <div className='col-md-2'>

                    <table className="table table-hover">
                        <thead>
                            <tr>
                                <th scope='col'>Body Target</th>
                                <th scope='col'>Exercise1</th>
                                <th scope='col'>Exercise2</th>
                                <th scope='col'>Exercise3</th>
                                <th scope='col'>Exercise4</th>
                                <th scope='col'>Exercise5</th>
                            </tr>
                        </thead>
                        <tr >

                            <th scope="row">{props.book.body_part}</th>
                            <th scope="row">{props.book.exercise1}</th>
                            <th scope="row">{props.book.exercise2}</th>
                            <th scope="row">{props.book.exercise3}</th>
                            <th scope="row">{props.book.exercise4}</th>
                            <th scope="row">{props.book.exercise5}</th>

                        </tr>
                    </table>


                </div>
                {/* <div className='col-md-6'>
                    <div className='card-body'>
                        <h5 className='card-title'>
                            {props.book.body_part}
                        </h5>
                        <h4>
                            {props.book.body_part}
                        </h4>
                        <p className='card-text'>
                            {props.book.date}
                        </p>
                    </div>
                </div> */}
                {/* <div className='col-md-4 d-flex justify-content-center align-items-center'>
                    <a className='btn btn-md main-color text-white' href='#'>
                        View Details
                    </a>
                </div> */}
            </div>
        </div>
    );
}