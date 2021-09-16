const Discord = require("discord.js");
const client = new Discord.Client();
const config = require("./config.json");
const schedule = require("node-schedule");
const moment = require("moment-timezone");
var SelfReloadJSON = require('self-reload-json');
var buildfile = new SelfReloadJSON('./builds.json');
const attendees = new Discord.Collection();
const raidDates = new Discord.Collection();
var basicCommands = ['!hello', '!buildcommands', '!raidcommands', '!author'];
var raidCommands = ['!raidcreate [raid name] [year] [month] [day] [hour0-23] [minute]', '!raidinfo', '!raidcancel [name]', '!attend [person] [class] [raid name]', '!attendlist', '!attendclose [raid name]', '!attendempty'];
var buildCommands = ['!builds', '!buildsadd [class] [build]', '!buildsremove [class] [build]'];

function pluck(array){
    return array.map(function(item){
        return item["name"];
    });
}

function hasRole(mem, role){
    if(pluck(mem.roles).includes(role)){
        return true;
    }
    else{
        return false;
    }
}

client.on('ready', () => {
    console.log("The bot is online!");
});

client.on("guildCreate", guild => {
    console.log(`New guild added : ${guild.name} owned by ${guild.owner.user.username}`);
});
client.on("guildMemberAdd", member => {
    let guild = member.guild;
    guild.defaultChannel.send(`Welcome ${member.user} to the Spectre Server!`);
});

client.on('message', message => {
    if(message.author.bot) return;
    if(!message.content.startsWith(config.prefix)) return;

    let command = message.content.split(" ")[0];
    command = command.slice(config.prefix.length);
    let args = message.content.split(" ").slice(1);
    
    if(command === "commands"){
        message.channel.send(basicCommands);
    }

    if(command === "raidcommands"){
        message.channel.send(raidCommands);
    }

    if(command === "buildcommands"){
        message.channel.send(buildCommands);
    }

    if(command === "hello"){
        message.channel.send("Hello there, " + message.author.username);
    }


    if(command === "raidcreate"){
        if(hasRole(message.member, "God mode") || hasRole(message.member, "King of Scrubs") || hasRole(message.member, "Master of Scrubs")){
            if(args.length === 6){
                if(args[0] != null && args[1] != null && args[2] != null && args[3] != null && args[4] != null && args[5] != null){
                    let jobList = schedule.scheduledJobs;
                    var cleanStr = args[0].replace(/[^\w\s]|_/g, "").replace(/\s+/g, " ");              
                    let jobCheck = "jobList." + cleanStr;
                    let remindCheck = "jobList." + cleanStr + "reminder";
                    if(eval(jobCheck != undefined) && eval(remindCheck) != undefined){
                        message.channel.send("A raid by this name already exists.");
                    }
                    else{
                        //var startTime = new Date(Date.now());
                        var date = new Date(args[1], args[2] - 1, args[3], args[4], args[5], "0");
                        var remindStart = new Date(args[1], args[2] - 1, args[3], "0", "0", "0");
                        message.guild.channels.find('name', 'gw2crystalcasino').send("```css\n" + cleanStr + " Raid is scheduled for " + moment.tz(date, config.timezone).format('MMMM Do YYYY, h:mm:ss a') + " USA PST and a top of the hour reminder is set, unless bot says invalid date.```");
                        var raidDay = schedule.scheduleJob(cleanStr, date, function() {
                            message.guild.channels.find('name', 'gw2crystalcasino').send("```css\nTHE " + cleanStr + " RAID IS NOW : " + moment.tz(date, config.timezone).format('MMMM Do YYYY, h:mm:ss a') + " USA PST.```");
                            let remind = cleanStr + "reminder";
                            let jobName = schedule.scheduledJobs[cleanStr];
                            let remindName = schedule.scheduledJobs[remind];
                            jobName.cancel();
                            remindName.cancel();
                            raidDates.delete(cleanStr);
                        });
                        var raidRemind = schedule.scheduleJob(cleanStr + "reminder", {start: remindStart, end: date, rule: '0 * * * *'}, function(){
                            message.guild.channels.find('name', 'gw2crystalcasino').send("```css\nThere is an upcoming "+ cleanStr + " Raid scheduled: " + moment.tz(date, config.timezone).format('MMMM Do YYYY, h:mm:ss a') +" USA PST.```");
                        });
                        console.log(schedule.scheduledJobs);
                        raidDates.set(cleanStr, date);
                    }      
                }
                else{
                    message.channel.send("Invalid function formatting (or input).");
                    message.channel.send("The format must be: " + raidCommands[0]);
                }
            }
            else{
                message.channel.send("Invalid function formatting (or input).");
                message.channel.send("The format must be: " + raidCommands[0]);
            }
        }
        else{
            message.channel.send("Insufficient permissions to perform this function.");
        }      
    }

    if(command === "raidinfo"){
        let raidArray = [];
        let nameArray = raidDates.keyArray();
        let dateArray = raidDates.array();
        for(let i = 0; i < raidDates.size; i++){
            var temp = moment.tz(dateArray[i], config.timezone).format('MMMM Do YYYY, h:mm:ss a');
            raidArray.push(nameArray[i] + " " + temp + " USA PST.");
        }
        if(raidDates.size === 0){
            message.channel.send("Currently no raids are scheduled.")
            message.author.send("Currently no raids are scheduled.");
        }
        else{
            message.channel.send(raidArray);
            message.author.send(raidArray);
        }
    }


    if(command === "raidcancel"){
        if(hasRole(message.member, "God mode") || hasRole(message.member, "King of Scrubs") || hasRole(message.member, "Master of Scrubs")){
            if(args[0] != null && args.length == 1){
                let jobList = schedule.scheduledJobs;              
                let cleanStr = args[0].replace(/[^\w\s]|_/g, "").replace(/\s+/g, " ");              
                let jobCheck = "jobList." + cleanStr;
                let remindCheck = "jobList." + cleanStr + "reminder";
                if(eval(jobCheck) != undefined && eval(remindCheck != undefined)){
                    var remind = cleanStr + "reminder";
                    var jobName = schedule.scheduledJobs[cleanStr];
                    var remindName = schedule.scheduledJobs[remind];
                    jobName.cancel();
                    remindName.cancel();
                    raidDates.delete(cleanStr);
                    message.channel.send("This raid event was cancelled.");
                    message.guild.channels.find('name', 'gw2crystalcasino').send("The " + cleanStr + " raid event has been cancelled.");
                }          
            }
            else{
                message.channel.send("Invalid function formatting (or input).");
                message.channel.send("The format must be: " + raidCommands[2]);
            }
        } 
        else{
            message.channel.send("Insufficient permissions to perform this function.");
        }     
    }

    

    if(command === "attend"){
        if(args.length === 3){
            if(args[0] != null && args[1] != null && args[2] != null){
                attendees.set(args[0], args[1] + " " + args[2]);
                message.channel.send("You have been added to the " + args[2] + " raid attendance list. Please contact the raid leader if you cannot attend.");
            }
            else{
                 message.channel.send("Invalid function formatting (or input).");
                 message.channel.send("The format must be: " + raidCommands[3]);
            }         
        }
        else{
             message.channel.send("Invalid function formatting (or input).");
             message.channel.send("The format must be: " + raidCommands[3]);
        }
        
    }

    if(command === "attendlist"){
        let stringArray = [];
        let attendArray = attendees.keyArray();
        let classArray = attendees.array();
        for(let i = 0; i < attendees.size; i++){
            stringArray.push(attendArray[i] + " " + classArray[i]);
        }
        if(attendees.size === 0){
            message.channel.send("Currently there is nobody signed up to attend.");
        }
        else{
            message.channel.send(stringArray);
        }
    }

    if(command === "attendclose"){
        if(hasRole(message.member, "God mode") || hasRole(message.member, "King of Scrubs") || hasRole(message.member, "Master of Scrubs")){
            if(args[0] != null){
                let keyArray = [];
                let valArray = [];
                let attendArray = attendees.keyArray();
                let classArray = attendees.array();
                for(let i = 0; i < attendees.size; i++){
                    valArray.push(attendArray[i] + " " + classArray[i]);
                }
                for(let i = 0; i < valArray.length; i++){
                    let key = valArray[i].split(" ")[0];
                    let parsedVal = valArray[i].split(" ").slice(1);
                    if(parsedVal[1] === args[0]){
                        keyArray.push(key);
                    }
                }
                for(let i = 0; i < keyArray.length; i++){
                    attendees.delete(keyArray[i]);
                }
                message.channel.send("The attendance for this raid has been closed out.");    
            }
            else{
                message.channel.send("Invalid function formatting (or input), or the raid does not exist.");
                message.channel.send("The format must be: "+ raidCommands[5]);
            }
        }
        else{
            message.channel.send("Insufficient permissions to perform this function.");
        }  
    } 

    if(command === "attendempty"){
        if(hasRole(message.member, "God mode") || hasRole(message.member, "King of Scrubs") || hasRole(message.member, "Master of Scrubs")){
            attendees.clear();
            message.channel.send("The attendance list has been scrubbed clean.");
        }
        else{
            message.channel.send("Insufficient permissions to perform this function.");
        }    
    }       

    if(command === "builds"){
        let buildString = [];
        for(var i = 0; i < buildfile.currentbuild.length; i++) {
            var d = buildfile.currentbuild[i];
            buildString.push(d.class + ' : ' + d.buildlink);
        }
        message.channel.send(buildString);
    }

    if(command === "buildsadd"){
        if(hasRole(message.member, "God mode") || hasRole(message.member, "King of Scrubs") || hasRole(message.member, "Master of Scrubs")){
            if(args[0] != null && args[1] != null){
                var key = args[0];
                var build = args[1];
                buildfile['currentbuild'].push({"class": key,"buildlink": build}); 
                message.channel.send("The build has been successfully added to this list.")  
            }
            else{
                message.channel.send("Invalid function formatting (or input).");
                message.channel.send("The format must be: " + buildCommands[1]);
            }                 
        }
        else{
            message.channel.send("Insufficient permissions to perform this function.");
        }
    }

    if(command === "buildsremove"){
        if(hasRole(message.member, "God mode") || hasRole(message.member, "King of Scrubs") || hasRole(message.member, "Master of Scrubs")){
            if(args[0] != null && args.length === 2){
                var key = args[0];
                var build = args[1];
                var helper = buildfile.currentbuild;
                for(var i in helper){
                    let classId = helper[i].class;
                    let buildId = helper[i].buildlink;
                    if(classId === key && buildId === build){
                        helper.splice(i, 1);
                        message.channel.send("Build has been successfully removed from the build sheet.");
                    }
                }
            }
            else{
                message.channel.send("Invalid function formatting (or input), or the build does not exist.");
                message.channel.send("The format must be: " + buildCommands[2]);
            }                  
        }
        else{
            message.channel.send("Insufficient permissions to perform this function.");
        }
    }

    if(command === "spam"){
        if(hasRole(message.member, "God mode") || hasRole(message.member, "King of Scrubs")){
            if(args[0] != null){
                var person = message.mentions.users.first();
                if(!person){
                    message.author.send("This person does not exist.");
                }
                else{
                    let startTime = new Date(Date.now());
                    let endTime = new Date(startTime.getTime() + 10000);
                    var j = schedule.scheduleJob({ start: startTime, end: endTime, rule: '* */1 * * * *' }, function(){
                        message.channel.send(person.toString() +  " this is what spam looks like. I don't spam, I remind you idiots of when the raids are and you still ask anyhow.");
                    });
                }               
            }
        }
    }


    if(command === "author"){
        message.channel.send("The man behind the curtain on this beautiful work of bot art is Jroh, and yes; he is a great and powerful Druid....and Wizard.");
        message.author.send("The man behind the curtain on this beautiful work of bot art is Jroh, and yes; he is a great and powerful Druid....and Wizard.");
    }
});

client.login(config.token);
