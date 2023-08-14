import { Button, Checkbox, FormControlLabel, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from '@mui/material';
import { useEffect, useState } from 'react';
import { NavLink } from 'react-router-dom';
import GymHistory from '../../models/GymHistory';
import { Pagination } from '../../utils/Pagination';
import { SpinnerLoading } from '../../utils/SpinnerLoading';
import { SearchBook } from './SearchBook';


export const GymHistoryTable = () => {

    const [gymHistory, setGymHistory] = useState<GymHistory[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [httpError, setHttpError] = useState(null);
    const [currentPage, setcurrentPage] = useState(1);
    const [bookPerPage] = useState(5);
    const [totalAmountofBooks, setTotalAmountofBooks] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const authToken = localStorage.getItem("authenticationToken");


    useEffect(() => {
        const fetchBooks = async () => {
            const baseUrl: string = "http://localhost:8080/api/gym/gymhistory";

            let url: string = `${baseUrl}/${currentPage - 1}/${bookPerPage}`;

            const response = await fetch(url , {
                method: 'GET',
                headers: {
                  'Authorization': `Bearer ${authToken}`
                },
              });

            if (!response.ok) {
                throw new Error('Something went wrong!');
            }

            const responseJson = await response.json();

            const responseData = responseJson;

            setTotalAmountofBooks(responseJson.total);
            setTotalPages(Math.trunc(responseJson.total / bookPerPage) + (responseJson.total % bookPerPage > 0 ? 1 : 0));

            const LoadedGymHistory: GymHistory[] = [];


            responseJson.content.forEach((history: GymHistory) => {
                LoadedGymHistory.push({
                    id: history.id,
                    exercise_id: history.exercise_id,
                    body_part: history.body_part,
                    exercise1: history.exercise1,
                    exercise2: history.exercise2,
                    exercise3: history.exercise3,
                    exercise4: history.exercise4,
                    exercise5: history.exercise5,
                    date: history.date
                });
            });
            setGymHistory(LoadedGymHistory);
            setIsLoading(false);
        };
        fetchBooks().catch((error: any) => {
            setIsLoading(false);
            setHttpError(error.message);
        })
        window.scrollTo(0, 0);
    }, [currentPage]);

    if (isLoading) {
        return (
            <SpinnerLoading />
        )
    }

    if (httpError) {
        return (
            <div className='container m-5'>
                <p>{httpError}</p>
            </div>
        )
    }

    const indexofLastBook: number = currentPage * bookPerPage;
    const indexofFirstBook: number = indexofLastBook - (bookPerPage);
    let lastItem = bookPerPage * currentPage <= totalAmountofBooks ? bookPerPage * currentPage : totalAmountofBooks;

    const paginate = (pageNumber: number) =>
        setcurrentPage(pageNumber);

    return (
        <div>
            <div className='container'>
                <div>

                    {/* <div className='mt-3'>
                        <h5>Number of results: (22)</h5>
                    </div>
                    <p>
                        1 to 5 of 22 items:
                    </p>
                    <table>
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
                            {gymHistory.map(book => (
                                <SearchBook book={book} key={book.id} />
                            ))}
                        </tr>
                    </table>



                    //table for history
                                


                    {totalPages > 1 &&
                        <Pagination currentPage={currentPage} totalPages={totalPages} paginate={paginate} />} */}

                    <TableContainer component={Paper}>
                        <Table sx={{ minWidth: 650 }} aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell>Date</TableCell>
                                    <TableCell align="right">Body Target</TableCell>
                                    <TableCell align="right">Exercise1</TableCell>
                                    <TableCell align="right">Exercise2</TableCell>
                                    <TableCell align="right">Exercise3</TableCell>
                                    <TableCell align="right">Exercise4</TableCell>
                                    <TableCell align="right">Exercise5</TableCell>

                                </TableRow>
                            </TableHead>

                            { Object.keys(gymHistory).length == 0 ? <div>No Records found</div> :
                            <TableBody>
                                {gymHistory.map((row) => (
                                    <TableRow
                                        key={row.id}
                                        sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                                    >
                                        <TableCell component="th" scope="row">{row.date} </TableCell>
                                        <TableCell align="right">{row.body_part} </TableCell>
                                        <TableCell align="right">{row.exercise1?.split("$")[0]} </TableCell>
                                        <TableCell align="right">{row.exercise2?.split("$")[0]} </TableCell>
                                        <TableCell align="right">{row.exercise3?.split("$")[0]} </TableCell>
                                        <TableCell align="right">{row.exercise4?.split("$")[0]} </TableCell>
                                        <TableCell align="right">{row.exercise5?.split("$")[0]}  </TableCell>

                                    </TableRow>
                                ))}
                            </TableBody>}


                        </Table>
                    </TableContainer>
                    {totalPages > 1 &&
                        <Pagination currentPage={currentPage} totalPages={totalPages} paginate={paginate} />}

                    <NavLink type='button' to="/workout">
                        Go Back</NavLink>
                </div>
            </div>
        </div>
    );
}