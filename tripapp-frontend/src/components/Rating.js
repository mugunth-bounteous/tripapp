import React, { useState } from 'react';

const Rating = () => {
  const [rating, setRating] = useState(0);

  const handleRatingClick = (newRating) => {
    setRating(newRating);
  };

  return (
    <div>
      <h2>Rating: {rating} stars</h2>
      <div className="rating">
        {[1, 2, 3, 4, 5].map((star) => (
          <span
            key={star}
            className={star <= rating ? 'star filled' : 'star'}
            onClick={() => handleRatingClick(star)}
          >
            ★
          </span>
        ))}
      </div>
    </div>
  );
};

export default Rating;
