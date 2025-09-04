import { useState } from 'react'
import { addUser } from '../../Service/UserService';
import { toast } from 'react-hot-toast';

const UserForm = ({ setUsers }) => {

    const [loading, setloading] = useState(false);
    const [data, setData] = useState({
        name: "",
        email: "",
        password: "",
        role: "ROLE_USER"
    });

    const onChangeHandler = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        setData((data) => ({ ...data, [name]: value }))
    }

    const onSubmitHandler = async (e) => {
        e.preventDefault();
        setloading(true);
        try {
            const response = await addUser(data);
            setUsers((prevUsers) => ([...prevUsers, response.data]));
            toast.success("User added successfully");
            setData({
                name: "",
                email: "",
                password: "",
                role: "ROLE_USER"
            });
        } catch (e) {
            console.log(e);
            toast.error("Error adding user");
        } finally {
            setloading(false);
        }
    }
    return (
        <div className="item-form-container" style={{ height: '100vh', overflowY: 'auto', overflowX: 'hidden' }}>

            <div className="mx-2 mt-2">
                <div className="row">
                    <div className="card col-md-12 form-container">
                        <div className="card-body">
                            <form onSubmit={onSubmitHandler}>

                                <div className="mb-3">
                                    <label htmlFor="name" className="form-label">Name</label>
                                    <input type="text" name="name" id="name" className="form-control" placeholder="Jhon Doe"
                                        onChange={onChangeHandler}
                                        value={data.name}
                                        required
                                    />
                                </div>

                                <div className="mb-3">
                                    <label htmlFor="email" className="form-label">Email</label>
                                    <input type="text" name="email" id="email" className="form-control" placeholder="yourname@gmail.com"
                                        onChange={onChangeHandler}
                                        value={data.email}
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <label htmlFor="password" className="form-label">Password</label>
                                    <input type="password" name="password" id="password" className="form-control" placeholder="**************"
                                        onChange={onChangeHandler}
                                        value={data.password}
                                        required
                                    />
                                </div>

                                <button type="submit" className="btn btn-warning w-100" disabled={loading} >
                                    {loading ? "Loading..." : "Save"}</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default UserForm