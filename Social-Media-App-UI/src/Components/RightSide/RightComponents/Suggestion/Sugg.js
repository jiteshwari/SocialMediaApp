import React from 'react'
import "../Suggestion/Sugg.css"

const Sugg = () => {
  return (
    <div className="Sugg-comp">
      <h2>Suggestion For You</h2>

      <div className="sugg-people">
        <div className="s-left">
          <img src="https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRXqO4pBeLoXaegT8aDlnPzNBT0j-EmFaYi9_iL_ZFCago1SNWD" alt="" />
          <h3>Cristiano Ronaldo</h3>
        </div>

        <div className="s-right">
          <button>Follow</button>
          <button>Dismiss</button>
        </div>
      </div>

      <div className="sugg-people">
        <div className="s-left">
          <img src="https://itforum.com.br/wp-content/uploads/2023/09/Elon-Musk.jpg?x87066" alt="" />
          <h3>Elon Musk</h3>
        </div>

        <div className="s-right">
          <button>Follow</button>
          <button>Dismiss</button>
        </div>
      </div>

    </div>
  )
}

export default Sugg 