
var openStream = function() {
    // {"topWords":
    //   {"you":8,"the":8,"que":4,"people":3,"this":3,"its":3,"bir":3,"dan":3,"about":2,"via":2,"daha":2,"perempuan":2,"natproductsshow":2,"brahmin":2,"kldaroluna":2}
    // }
    var eventSource = new EventSource("/twitter-bubbles/top-words");

    eventSource.onmessage = function (e) {
        console.log("Processing message: ", e.data);
        render(JSON.parse(e.data));
    };

    eventSource.onerror = function (e) {
        console.log("EventSource failed.", e);
    };
};

// Bubbles based on: http://scottbw.github.io/d3js-bubble-example

function mapEntries(data) {
    var dataframe = [];

    Object.keys(data.topWords).forEach(function (word) {
        dataframe.push([word, data.topWords[word]]);
    });

    return dataframe;
}

function drawBubbleChart(root) {
    //
    // How big the chart is
    //
    var width = 1680;
    var height= 1050;

    //
    // Pick some colours for the categories (groups)
    //
    var color = d3.scale.category10();

    //
    // Create a bubble layout based on the tree of objects.
    // This adds properties x,y,r to each of our leaf objects
    // indicating where to draw them (x,y), and how big to draw them (r).
    // This is worked out using the "value" property of each leaf.
    //
    var bubble = d3.layout.pack().sort(null).size([width, height]).padding(1.5);

    // remove old svg element(s)
    var oldSvgElements = document.body.getElementsByTagName("svg");
    for (var i = 0; i < oldSvgElements.length; i++) {
        document.body.removeChild(oldSvgElements[i]);
    }

    //
    // Make a SVG graphic
    //
    var svg = d3.select("body")
        .append("svg")
        .attr("width", width)
        .attr("height", height)
        .attr("class", "bubble");

    //
    // For each leaf, create a new "node" and place it in the correct
    // location using the transform attribute.
    //
    var node = svg.selectAll(".node")
        .data(bubble.nodes(root)
            .filter(function (d) {
                return !d.children;
            }))
        .enter()
        .append("g")
        .attr("class", "node")
        .attr("transform", function (d) {
            return "translate(" + d.x + "," + d.y + ")";
        });

    //
    // For each node, make a circle of the correct radius (r) and
    // colour it in according to the group it belongs to
    //
    node.append("circle")
        .attr("r", function (d) {
            return d.r;
        })
        .style("fill", function (d) {
            return color(d.group)
        });

    //
    // For each node, add a label to the middle of the circle
    //
    node.append("text")
        .attr("dy", ".3em")
        .style("text-anchor", "middle")
        .text(function (d) {
            return d.name;
        });
}

function render(data) {
    var dataframe = mapEntries(data);

    var root = {
        name: "Top Words",
        children: []
    };

    var group = 1;

    for (var i = 0; i < dataframe.length; i++) {
        var word = dataframe[i][0];
        var count = dataframe[i][1];
        root.children.push({
            name: word + ": " + count,
            value: Number(count),
            group: group++
        });
    }

    drawBubbleChart(root);
}

