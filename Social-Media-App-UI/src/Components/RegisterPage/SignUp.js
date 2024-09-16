import React, { useEffect, useState } from 'react';
import "../RegisterPage/RegisterPage.css";
import { AiOutlineUser } from "react-icons/ai";
import { FiMail } from "react-icons/fi";
import { RiLockPasswordLine } from "react-icons/ri";
import { Link, useNavigate } from 'react-router-dom';
import validation from './Validation';
import { registerUser } from '../../Configs/ApiService';
 // Import the API service function

const SignUp = () => {
    const navigate = useNavigate();
    const [error, setError] = useState({});
    const [submit, setSubmit] = useState(false);
    const [showAdditionalFields, setShowAdditionalFields] = useState(false);

    const [data, setData] = useState({
        fullname: "",
        email: "",
        password: "",
        confirmpassword: "",
        bio: "",
        profilepic: null
    });

    const handleChange = (e) => {
        const { name, value, files } = e.target;
        const newObj = { ...data, [name]: files ? files[0] : value };
        setData(newObj);
    };

    const handleNext = () => {
        setShowAdditionalFields(true);
    };

    const handleSignUp = async (e) => {
        e.preventDefault();
        console.log(data);
        const validationErrors = validation(data);
        setError(validationErrors);
        setSubmit(true);

        if (Object.keys(validationErrors).length === 0) {
            const formData = new FormData();
            formData.append('fullname', data.fullname);
            formData.append('email', data.email);
            formData.append('password', data.password);
            formData.append('confirmpassword', data.confirmpassword);
            formData.append('bio', data.bio);
            if (data.profilepic) {
                formData.append('profilepic', data.profilepic);
            }
            console.log(formData);

            try {
                const response =await registerUser(data);
                if (response.status === 200 && response.data) {
                    // Store the token if it exists (optional)
                    if (response.data !== null) {
                        navigate("/login");
                    }

            
                  
                } else {
                    // Set error if the login was not successful
                    setError({ general: 'Error during registration' });
                }
            } catch (error) {
                console.error("Error during registration:", error);
                // Handle error (e.g., show a message to the user)
            }
        }
        else{
            console.log("Failed validation");
        }
    };

    useEffect(() => {
        if (Object.keys(error).length === 0 && submit) {
            navigate("/home");
        }
    }, [error]);

    return (
        <div className="container">
            <div className="container-form">
                <form onSubmit={handleSignUp}>
                    <h1>Create Account</h1>
                    <p>Please fill the input below here.</p>

                    {!showAdditionalFields && (
                        <>
                            <div className="inputBox">
                                <AiOutlineUser className='fullName' />
                                <input type='text'
                                    name="fullname"
                                    id="fullname"
                                    onChange={handleChange}
                                    placeholder='First Name'
                                />
                            </div>
                            <div className="inputBox">
                                <AiOutlineUser className='fullName' />
                                <input type='text'
                                    name="lastName"
                                    id="lastName"
                                    onChange={handleChange}
                                    placeholder='LastName Name'
                                />
                            </div>
                            {error.fullname && <span style={{ color: "red", display: "block", marginTop: "5px" }}>{error.fullname}</span>}

                            <div className="inputBox">
                                <FiMail className='mail' />
                                <input type="email"
                                    name="email"
                                    id="email"
                                    onChange={handleChange}
                                    placeholder='Email'
                                />
                            </div>
                            {error.email && <span style={{ color: "red", display: "block", marginTop: "5px" }}>{error.email}</span>}

                            <div className="inputBox">
                                <RiLockPasswordLine className='password' />
                                <input type="password"
                                    name="password"
                                    id="password"
                                    onChange={handleChange}
                                    placeholder='Password'
                                />
                            </div>
                            {error.password && <span style={{ color: "red", display: "block", marginTop: "5px" }}>{error.password}</span>}

                            <div className="inputBox">
                                <RiLockPasswordLine className='password' />
                                <input type="password"
                                    name="confirmpassword"
                                    id="confirmPassword"
                                    onChange={handleChange}
                                    placeholder='Confirm Password'
                                />
                            </div>
                            {error.confirmpassword && <span style={{ color: "red", display: "block", marginTop: "5px" }}>{error.confirmpassword}</span>}

                            <div className='divBtn'>
                                <button type="button" className='loginBtn' onClick={handleNext}>Next</button>
                            </div>
                        </>
                    )}

                    {showAdditionalFields && (
                        <>
                            <div className="inputBox">
                                <textarea
                                    name="bio"
                                    id="bio"
                                    onChange={handleChange}
                                    placeholder='Tell us something about yourself (optional)'
                                    rows="3"
                                    style={{ width: "100%", padding: "5px 10px", fontSize: "16px", borderRadius: "5px", border: "1px solid grey" }}
                                />
                            </div>

                            <div className="inputBox">
                                <input
                                    type="file"
                                    name="profilepic"
                                    id="profilepic"
                                    onChange={handleChange}
                                    accept="image/*"
                                    style={{ padding: "5px 10px", borderRadius: "5px", border: "1px solid grey" }}
                                />
                                <label htmlFor="profilepic" style={{ color: "grey", fontSize: "14px" }}>Upload Profile Picture (optional)</label>
                            </div>

                            <div className='divBtn'>
                                <small className='FG'>Forgot Password?</small>
                                <button className='loginBtn' type='submit'>SIGN UP</button>
                            </div>
                        </>
                    )}
                </form>

                <div className='dont'>
                    <p>Already have an account? <Link to="/"><span>Sign in</span></Link></p>
                </div>
            </div>
        </div>
    )
}

export default SignUp;
