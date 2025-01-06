const Toolbar = ({setTool, setColor, setLineWidth, clearWhiteboard}) => {
    <div className="toolbar">
        {/*Buttons to be shown in the toolbar.*/}
        <button onClick={() => setTool('pen')}>Pen</button>
        <button onClick={() => setTool('eraser')}>Eraser</button>

        {/*Color picker to set the drawing color.*/}
        <input type="color" onChange={(e) => setColor(e.target.value)}/>

        {/*Range input slider to adjust the line width.*/}
        <input
            type="range"
            min="1"
            max="10"
            onChange={(e) => setLineWidth(e.target.value)}
        />

        {/*Button to clear the whiteboard.*/}
        <button onClick={clearWhiteboard}>Clear</button>
    </div>
}