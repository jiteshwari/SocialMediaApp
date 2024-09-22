import "../Post/InputPost.css";
import PhotoLibraryIcon from '@mui/icons-material/PhotoLibrary';
import CloseRoundedIcon from '@mui/icons-material/CloseRounded';
import PlayCircleFilledOutlinedIcon from '@mui/icons-material/PlayCircleFilledOutlined';
import KeyboardVoiceRoundedIcon from '@mui/icons-material/KeyboardVoiceRounded';
import { FaSmile } from "react-icons/fa";
import React, { useState } from 'react';

const InputPost = ({ handleSubmit, setBody, body, images, setImages, profileImg }) => {
    const [uploadStatus, setUploadStatus] = useState('');

    const handleShare = async (e) => {
        e.preventDefault();
        
        const formData = new FormData();
        formData.append('contentType', 'image'); // You can adjust this as needed
        formData.append('caption', body);
        formData.append('userId', 1); // Replace with actual user ID
        if (images) {
            formData.append('imageFile', images);
        }

        console.log('Submitting form data:', {
            caption: body,
            userId: 1,
            images,
        });

        try {
            const response = await fetch('http://localhost:8082/api/posts/uploadImagePost', {
                method: 'POST',
                body: formData,
            });

            if (response.ok) {
                const responseData = await response.text();
                setUploadStatus(`Post shared successfully: ${responseData}`);
                setBody(''); // Clear the input
                setImages(null); // Clear the image after sharing
            } else {
                const errorMessage = await response.text();
                setUploadStatus(`Error: ${errorMessage}`);
            }
        } catch (error) {
            console.error('Error uploading post:', error);
            setUploadStatus('An error occurred while sharing the post.');
        }
    };

    return (
        <div className="i-form">
            <form onSubmit={handleShare}>
                <div className="i-input-box">
                    <img src={profileImg} className='i-img' />
                    <input
                        type="text"
                        id="i-input"
                        placeholder="What's in your mind?"
                        required
                        value={body}
                        onChange={(e) => setBody(e.target.value)}
                    />
                </div>

                <div className="file-upload">
                    <div className="file-icons">
                        <label htmlFor="file" className="pv-upload">
                            <PhotoLibraryIcon className="input-svg" style={{ fontSize: "38px", color: "orangered" }} />
                            <span className='photo-dis'>Photo</span>
                        </label>
                        <div className="pv-upload">
                            <PlayCircleFilledOutlinedIcon className="input-svg" style={{ fontSize: "38px", color: "black" }} />
                            <span className='photo-dis'>Video</span>
                        </div>
                        <div className="pv-upload">
                            <KeyboardVoiceRoundedIcon className="input-svg" style={{ fontSize: "38px", color: "green" }} />
                            <span className='photo-dis'>Audio</span>
                        </div>
                        <div className="pv-upload">
                            <FaSmile className="input-svg" style={{ fontSize: "30px", color: "red" }} />
                            <span className='photo-dis'>Feelings/Activity</span>
                        </div>
                    </div>
                    <button type='submit'>Share</button>
                </div>

                <div style={{ display: "none" }}>
                    <input
                        type="file"
                        id="file"
                        accept=".png,jpeg,.jpg"
                        onChange={(e) => setImages(e.target.files[0])}
                    />
                </div>

                {images && (
                    <div className="displayImg">
                        <CloseRoundedIcon onClick={() => setImages(null)} />
                        <img src={URL.createObjectURL(images)} alt="" />
                    </div>
                )}

                {uploadStatus && <p>{uploadStatus}</p>}
            </form>
        </div>
    );
};

export default InputPost;
