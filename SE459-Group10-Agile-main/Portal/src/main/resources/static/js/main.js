// Declaring needed variables
let started
let algorithm = 'Depth First Search'
let startButton
let screen
let graph
let resolution
let openSet
let closedSet
let source;
let destination;
let shortestPath
let w;
let h;
let sourceSelected
let destinationSelected
//////////////////////////
// Our Stuff 
let mainDivHeight
let mainDivWidth

let floorPlanJson
let floorPlanGraph
let floorPlanDirt
let floorPlanWalls

let floorPlanRows
let floorPlanColumns

let cleanButton

// when the DOM object is setup (i.e. ready), do what needs to be done as setup here. 
$(document).ready(function() {
    getDefaultFloorPlan();
    mainDivHeight = $(".main-content").height() / 1;
    mainDivWidth = $(".main-content").width() / 1.5;
    console.log('main-content Width: ' + mainDivWidth);
    console.log('main-content Height: ' + mainDivHeight);
    cleanButton = $('#cleanButton');

    setTimeout(() => {
        setup();
    }, 2000);
});

// Get the JSON maps from the server.
function getDefaultFloorPlan() {
    $.getJSON("js/pre_defined_map.json", function(json) {
        floorPlanJson = json;
        floorPlanGraph = json.graph;
        floorPlanWalls = json.walls;
        floorPlanDirt = json.dirt;
    
        floorPlanRows = floorPlanGraph.length;
        floorPlanColumns = floorPlanGraph[0].length;
    
        // DEBUG STATEMENTS for the json file loading in.
        // console.log('FloorPlanGraph Rows: '+ floorPlanRows);
        // console.log('FloorPlanGraph Columns: '+ floorPlanColumns);
        // console.log('Json: ' + JSON.stringify(floorPlanJson));
        // console.log('Graph: ' + floorPlanGraph);
        // console.log('Walls: ' + floorPlanWalls);
        // console.log('Dirt: ' + floorPlanDirt);
     });
}

// making the P5.js canvas 
function setup() {
    screen = createCanvas(500, 500);
    screen.parent("sketch01");
    screen.style('position','');
    resetCanvas();
    // setupWalls();
}

function resetCanvas() {
    // Initializing variables
    started = false
    resolution = 50
    openSet = []
    closedSet = []
    shortestPath = []
    sourceSelected = false
    destinationSelected = false

    graph = twoDArray(floorPlanRows, floorPlanColumns);

    // console.log('Graph Columns: ' + graph.length);
    // console.log('Graph Rows: ' + graph[0].length)
    // creating the graph 
    for (let i = 0; i < floorPlanColumns; i++) {
        for (let j = 0; j < floorPlanRows; j++) {
            graph[i][j] = new Node(i, j);
        }
    }
    // determining neighbors of each vertices
    for (let i = 0; i < floorPlanColumns; i++) {
        for (let j = 0; j < floorPlanRows; j++) {
            graph[i][j].addNeighbor();
        }
    }
    // Initializing random source and destination if not chosen
    // if (source === undefined || destination === undefined) {

    //     x = Math.floor(Math.random() * floorPlanColumns / 2)
    //     y = Math.floor(Math.random() * floorPlanRows)

    //     source = graph[x][y];

    //     x = Math.floor(Math.random() * (floorPlanColumns - Math.floor((floorPlanColumns / 2 + 1)))) + Math.floor((floorPlanColumns / 2 + 1));
    //     y = Math.floor(Math.random() * floorPlanRows)

    //     destination = graph[x][y];
    // }
    // // otherwise Reinitializing old source & destination from graph's new objects
    // else {
    //     graph.forEach(row => {
    //         row.forEach((node) => {
    //             if (node.i === source.i && node.j === source.j) {
    //                 source = node
    //             }
    //             if (node.i === destination.i && node.j === destination.j) {
    //                 destination = node
    //             }
    //         })
    //     })
    // }
    // //making sure source and destination aren't obstacls;
    // source.obstacle = false;
    // destination.obstacle = false;

    background(13,17,23);
    // revealing the canvas on screen
    for (let i = 0; i < floorPlanColumns; i++) {
        for (let j = 0; j < floorPlanRows; j++) {
            graph[i][j].show(255);
        }
    }
    // source.show(color(87, 50, 168));
    // destination.show(color(140, 68, 20));
    noLoop();
    screen.style('position','');

    // console.log(openSet)
}

function Node(i, j) {
    this.i = i;
    this.j = j;
    this.x = this.i * resolution;
    this.y = this.j * resolution;
    this.r = resolution - 1;

    this.obstacle = false;
    this.parent = undefined;
    this.neighbors = []
    this.dragging = false
    
    this.addTopWall = (x,y,r) => {
        stroke(237, 34, 93);
        strokeWeight(5);
        rect(x, y-2, r, r);
    }

    this.addBottomWall = (x,y,r) => {
        stroke(237, 34, 93);
        strokeWeight(5);
        rect(x, y+2, r, r);
    }

    this.addLeftWall = (x,y,r) => {
        stroke(237, 34, 93);
        strokeWeight(5);
        rect(x-2, y, r, r);
    }

    this.addRightWall = (x,y,r) => {
        strokeWeight(5);
        stroke(237, 34, 93);
        rect(x, y, r+2, r);
    }

    this.show = (color, direction) => {
        // console.log(color)
        let x = this.x;
        let y = this.y;
        let r = this.r;

        // Fill as obstacle
        if (this.obstacle) {
            fill(128, 128, 128);
        }
        else {
            fill(color);
        }



        // If direction is specified; draw an additional rect over top with the appropriate orientation.
        switch(direction) {
            case 'Top':
                this.addTopWall(x,y,r);
                break;
            case 'Bottom':
                this.addBottomWall(x,y,r);
                break;
            case 'Left':
                this.addLeftWall(x,y,r);
                break;
            case 'Right':
                this.addRightWall(x,y,r);
            default:
                break;
        }

        stroke(0);
        strokeWeight(5);
        rect(x, y, r, r);
    }
    this.addNeighbor = () => {

        let i = this.i;
        let j = this.j;
        //Orthogonal neighbors
        if (i > 0) this.neighbors.push(graph[i - 1][j]);
        if (i < floorPlanColumns - 1) this.neighbors.push(graph[i + 1][j]);
        if (j > 0) this.neighbors.push(graph[i][j - 1]);
        if (j < floorPlanRows - 1) this.neighbors.push(graph[i][j + 1]);
    }

    this.clicked = () => {
        if (sourceSelected) {
            this.show(color(87, 50, 168))
        }
        else if (destinationSelected) {
            this.show(color(140, 68, 20))
        }
        else if (!this.obstacle) {
            this.obstacle = true;
            this.show(color(128, 128, 128));
        }
    }
}

function twoDArray(rows, cols) {
    let arrays = new Array(cols);
    for (let i = 0; i < arrays.length; i++) {
        arrays[i] = new Array(rows)
    }
    return arrays;
}

function draw() {
    if (started) {
        // Algorithm for Breadth First Search
        if (algorithm == "Breadth First Search") {
            if (openSet.length > 0) {
                current = openSet[0]
                if (current == destination) {
                    noLoop();
                    // console.log("We're Done!")
                }

                //removing the "current" vertex from openSet and adding it to closedSet
                var removeIndex = openSet.map(function (item) { return item; }).indexOf(current);
                openSet.splice(removeIndex, 1);
                // console.log(openSet)
                for (neighbor of current.neighbors) {
                    if (!closedSet.includes(neighbor) && !neighbor.obstacle) {
                        openSet.push(neighbor);
                        closedSet.push(neighbor);
                        neighbor.parent = current
                    }
                }
            }
            else {
                // console.log('no solution');
                noLoop();
                return;
            }
        }

        // Algorithm for Depth First Search
        if (algorithm == "Depth First Search") {
            if (openSet.length > 0) {
                // console.log(openSet)
                current = openSet[openSet.length - 1]
                if (current == destination) {
                    noLoop();
                    // console.log("We're Done!")
                }

                //removing the "current" vertex from openSet and adding it to closedSet
                var removeIndex = openSet.map(function (item) { return item; }).indexOf(current);
                openSet.splice(removeIndex, 1);
                // console.log(openSet)
                for (neighbor of current.neighbors) {
                    if (!closedSet.includes(neighbor) && !neighbor.obstacle) {
                        openSet.push(neighbor);
                        closedSet.push(neighbor);
                        neighbor.parent = current
                    }
                }
            }
            else {
                // console.log('no solution');
                noLoop();
                return;
            }
        }

        background(13,17,23);

        // revealing the canvas on screen
        for (let i = 0; i < floorPlanColumns; i++) {
            for (let j = 0; j < floorPlanRows; j++) {
                graph[i][j].show(255);
            }
        }

        //Coloring the visited, unvisited vertices and the shortest path
        for (node of openSet) {
            node.show(color(45, 196, 129));
        }
        for (node of closedSet) {
            node.show(color(255, 0, 0, 50));
        }
        //initialize shortestPath array first
        shortestPath = [];
        let temp = current;
        shortestPath.push(temp);
        while (temp.parent) {
            shortestPath.push(temp.parent);
            temp = temp.parent;
        }

        noFill();
        stroke(255, 0, 200);
        strokeWeight(4);
        beginShape();
        for (path of shortestPath) {
            vertex(path.i * resolution + resolution / 2, path.j * resolution + resolution / 2);
        }
        endShape();
        source.show(color(87, 50, 168));
        destination.show(color(140, 68, 20));
    }

}

// Makes server call to run the SensorsCollection clean. 
// NOTE:    No idea yet how this will be realtime without making tons of server calls...
//          Potentially could just fake it by dupping the algorithm in javascript.
function clean() {
    console.log('Start Cleaning Robot!');
    start();
}

// Makes server call to save the floor plan.
function updateFloorPlan() {
    toastr.info('Uploading Floor Plan!');
   
    $.ajax(
    { 
        type: "POST",
        url: "/FloorPlan", 
        data: {
            "floorPlan" : JSON.stringify(floorPlanJson),
        },
        success: function(result){
            if(result){
                toastr.info(result + ' Uploaded Successfully.');
            } else {
                toastr.error("Failed to Upload Floor Plan.");
            }
        $("#div1").html(result);
        },
        error: function(xhr) {
            toastr.error(xhr);
        }
    });
}

// Resets the canvas back to the original pre_defined_map.json
async function resetFloorPlan() {
    console.log('Reseting to original Floor Plan!');
    // ajax call to get default floor plan.
    await getDefaultFloorPlan();
    // create new canvas after the floorplan has been re-loaded;
    setup();
    

}

function start() {
    if (algorithm != "Breadth First Search" && algorithm != "Depth First Search") {
        initialize()
    }
    else {
        BFSorDFS_initialize()
    }
    started = true;
    loop();
}

function initialize() {
    openSet.push(source);
}

function BFSorDFS_initialize() {
    openSet.push(source);
    closedSet.push(source)

}

function setupWalls() {

    // console.log(floorPlanWalls);
    // Columns
    for(let i = 0; i < floorPlanWalls.length; i++) {
        // Rows
        for(let j = 0; j < floorPlanWalls[0].length; j++) {

            if(floorPlanWalls[i][j] == 1){
                // // // need to create full wall (5 up ... 4 down)
                // console.log(graph[i][j]);
                // j is x-axis 
                // Example: j = 45; node[4] paint right edge as wall;
                // i is y-axis
                // expandWalls(graph[i][j]);
                // // stroke(255, 204, 0);
                // // strokeWeight(4);
                // // graph[i][j].obstacle = true;
                // // graph[i][j].show();
            }
        }
    }

    function expandWalls(node) {
        node.parentElement.css('background-color', 'red');
    }


    // for (let i = 0; i < floorPlanColumns; i++) {
    //     for (let j = 0; j < floorPlanRows; j++) {
    //         if (graph[i][j] != source && graph[i][j] != destination) {
    //             if(floorPlanWalls[i][j] == 1){
    //                 graph[i][j].obstacle = true;
    //                 graph[i][j].show();
    //              }
    //         }
    //     }
    // }
}

function mouseDragged() {
    if(started){
        return
    }
    for (let i = 0; i < floorPlanColumns; i++) {
        for (let j = 0; j < floorPlanRows; j++) {
            if (mouseX >= graph[i][j].x && mouseX <= graph[i][j].x + graph[i][j].r && mouseY >= graph[i][j].y && mouseY <= graph[i][j].y + graph[i][j].r) {
                if (graph[i][j] != source && graph[i][j] != destination) {
                    graph[i][j].clicked();
                }
                if (sourceSelected) {
                    source.show(255)
                    source = graph[i][j]
                    graph[i][j].clicked();
                }
                if (destinationSelected) {
                    // change prev source's color
                    destination.show(255)
                    destination = graph[i][j]
                    graph[i][j].clicked();
                }
            }
        }
    }
}

function mousePressed() {
    if(started){
        return
    }
    for (let i = 0; i < floorPlanColumns; i++) {
        for (let j = 0; j < floorPlanRows; j++) {
            if (mouseX >= graph[i][j].x && mouseX <= graph[i][j].x + graph[i][j].r && mouseY >= graph[i][j].y && mouseY <= graph[i][j].y + graph[i][j].r) {
                if (graph[i][j] != source && graph[i][j] != destination) {
                    graph[i][j].clicked();
                }
                else {
                    if (source === graph[i][j]) {
                        sourceSelected = true
                    }
                    if (destination === graph[i][j]) {
                        destinationSelected = true
                    }
                }
            }
        }
    }
}

function mouseReleased() {
    if (sourceSelected || destinationSelected) {
        for (let i = 0; i < floorPlanColumns; i++) {
            for (let j = 0; j < floorPlanRows; j++) {
                if (mouseX >= graph[i][j].x && mouseX <= graph[i][j].x + graph[i][j].r && mouseY >= graph[i][j].y && mouseY <= graph[i][j].y + graph[i][j].r) {
                    if (sourceSelected) {
                        if (graph[i][j] === destination) {
                            source = graph[i - 1][j]
                            source.obstacle = false
                            graph[i][j].show(color(140, 68, 20))
                            source.show(color(87, 50, 168))
                            sourceSelected = false
                        }
                        else {
                            source = graph[i][j]
                            source.obstacle = false
                            source.show(color(87, 50, 168))
                            sourceSelected = false
                        }
                    }
                    else {
                        if (graph[i][j] === source) {

                            destination = graph[i - 1][j]
                            destination.obstacle = false
                            source.show(color(87, 50, 168))
                            destination.show(color(140, 68, 20))
                            destinationSelected = false
                        }
                        else {
                            destination = graph[i][j]
                            destination.obstacle = false
                            destination.show(color(140, 68, 20))
                            destinationSelected = false
                        }
                    }
                }
            }
        }
    }
}

function heuristic(node, goal) {
    //euclidean distance
    // dx = abs(node.x - goal.x);
    // dy = abs(node.y - goal.y);
    // return 1 * sqrt(dx * dx + dy * dy);

    //Manhattan distance
    dx = abs(node.x - goal.x);
    dy = abs(node.y - goal.y);
    return 1 * (dx + dy);


    // let d = dist(a.i, a.j, b.i, b.j);
    // let d = abs(a.i - b.i) + abs(a.j - b.j);
    // return d;
}

function lowestFscoreNode() {
    let minNode = openSet[0];
    for (node of openSet) {
        if (node.f < minNode.f) {
            minNode = node;
        }
    }
    return minNode;
}

function lowestDscoreNode() {
    let minNode = openSet[0];
    for (node of openSet) {
        if (node.d < minNode.d) {
            minNode = node;
        }
    }
    return minNode;
}

function lowestHeuristicNode() {
    let minNode = openSet[0];
    for (node of openSet) {
        if (node.h < minNode.h) {
            minNode = node;
        }
    }
    return minNode;
}
