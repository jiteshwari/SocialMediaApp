import "../Post/InputPost.css"; // Import the same CSS file
import PhotoLibraryIcon from '@mui/icons-material/PhotoLibrary';
import CloseRoundedIcon from '@mui/icons-material/CloseRounded';
import React, { useState } from 'react';

const ImageUploadComponent = ({ profileImg, userName }) => {
    const [caption, setCaption] = useState('');
    const [imageFile, setImageFile] = useState(null);
    const [uploadStatus, setUploadStatus] = useState('');

    const handleFileChange = (e) => {
        setImageFile(e.target.files[0]);
    };

    const handleUpload = async (e) => {
        e.preventDefault();

        if (!imageFile) {
            setUploadStatus('Please select an image file to upload.');
            return;
        }

        const formData = new FormData();
        formData.append('contentType', 'image');
        formData.append('caption', caption);
        formData.append('userId', 1); // Replace with actual user ID
        formData.append('imageFile', imageFile);

        console.log('Submitting form data:', {
            caption,
            userId: 1,
            imageFile,
        });

        try {
            const response = await fetch('https://contentmicroservice-94526523070.asia-south1.run.app/api/posts/uploadImagePost', {
                method: 'POST',
                body: formData,
            });

            if (response.ok) {
                const responseData = await response.text();
                setUploadStatus(`Image uploaded successfully: ${responseData}`);
                setCaption(''); // Clear the input
                setImageFile(null); // Clear the image after uploading
            } else {
                const errorMessage = await response.text();
                setUploadStatus(`Error: ${errorMessage}`);
            }
        } catch (error) {
            console.error('Error uploading image:', error);
            setUploadStatus('An error occurred while uploading the image.');
        }
    };

    return (
        <div className="i-form">
            <form onSubmit={handleUpload}>
                <div className="i-input-box">
                    <img src={profileImg} className='i-img' alt="Profile"  />
                    <span className='user-name'>{userName}</span>
                    <input
                       style={{margin: 50 +'px'}}
                        type="text"
                        placeholder="Whats on your mind..."
                        required
                        value={caption}
                        onChange={(e) => setCaption(e.target.value)}
                    />
                </div>

                <div className="file-upload">
                    <label htmlFor="file" className="pv-upload">
                        <PhotoLibraryIcon className="input-svg" style={{ fontSize: "38px", color: "orangered" }} />
                        <span className='photo-dis'>Choose Image</span>
                    </label>
                    <input
                        type="file"
                        id="file"
                        accept=".png,jpeg,.jpg"
                        onChange={handleFileChange}
                        style={{ display: 'none' }}
                        required
                    />
                    <button type='submit'>Share</button>
                </div>

                {imageFile && (
                    <div className="displayImg">
                        <CloseRoundedIcon onClick={() => setImageFile(null)} />
                        <img src={URL.createObjectURL(imageFile)} alt="Selected" />
                    </div>
                )}

                {uploadStatus && <p>{uploadStatus}</p>}
            </form>
        </div>
    );
};

export default ImageUploadComponent;
